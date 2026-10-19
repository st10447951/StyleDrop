using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using StyleDrop.Api.Data;
using StyleDrop.Api.DTOs;
using StyleDrop.Api.Models;
using System.Security.Claims;

namespace StyleDrop.Api.Controllers;

[ApiController]
[Authorize]
[Route("api/sync")]
public class SyncController(StyleDropDbContext db) : ControllerBase
{
    private Guid UserId => Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [HttpPost]
    public async Task<ActionResult<SyncResponse>> Sync(SyncRequest request)
    {
        var now = DateTime.UtcNow;

        foreach (var dto in request.Wardrobe)
        {
            var existing = await db.WardrobeItems.FirstOrDefaultAsync(x =>
                x.UserId == UserId && x.ClientId == dto.ClientId);

            if (existing == null)
            {
                db.WardrobeItems.Add(new WardrobeItem
                {
                    Id = Guid.NewGuid(), UserId = UserId, ClientId = dto.ClientId,
                    Name = dto.Name, Category = dto.Category, Brand = dto.Brand,
                    Colour = dto.Colour, Size = dto.Size, ImageUrl = dto.ImageUrl,
                    Notes = dto.Notes, Favourite = dto.Favourite,
                    CreatedAt = now, UpdatedAt = dto.UpdatedAt, IsDeleted = dto.IsDeleted
                });
            }
            else if (dto.UpdatedAt >= existing.UpdatedAt)
            {
                existing.Name = dto.Name; existing.Category = dto.Category;
                existing.Brand = dto.Brand; existing.Colour = dto.Colour;
                existing.Size = dto.Size; existing.ImageUrl = dto.ImageUrl;
                existing.Notes = dto.Notes; existing.Favourite = dto.Favourite;
                existing.IsDeleted = dto.IsDeleted;
                existing.UpdatedAt = dto.UpdatedAt;
            }
        }

        foreach (var dto in request.Outfits)
        {
            var existing = await db.Outfits.FirstOrDefaultAsync(x =>
                x.UserId == UserId && x.ClientId == dto.ClientId);

            if (existing == null)
            {
                db.Outfits.Add(new Outfit
                {
                    Id = Guid.NewGuid(), UserId = UserId, ClientId = dto.ClientId,
                    Name = dto.Name, TopId = dto.TopId, BottomId = dto.BottomId,
                    ShoesId = dto.ShoesId, OuterwearId = dto.OuterwearId,
                    AccessoryId = dto.AccessoryId, Favourite = dto.Favourite,
                    CreatedAt = now, UpdatedAt = dto.UpdatedAt, IsDeleted = dto.IsDeleted
                });
            }
            else if (dto.UpdatedAt >= existing.UpdatedAt)
            {
                existing.Name = dto.Name; existing.TopId = dto.TopId;
                existing.BottomId = dto.BottomId; existing.ShoesId = dto.ShoesId;
                existing.OuterwearId = dto.OuterwearId; existing.AccessoryId = dto.AccessoryId;
                existing.Favourite = dto.Favourite; existing.IsDeleted = dto.IsDeleted;
                existing.UpdatedAt = dto.UpdatedAt;
            }
        }

        await db.SaveChangesAsync();

        var wardrobe = await db.WardrobeItems
            .Where(x => x.UserId == UserId)
            .OrderBy(x => x.UpdatedAt)
            .Select(x => new WardrobeItemDto
            {
                ClientId = x.ClientId, Name = x.Name, Category = x.Category,
                Brand = x.Brand, Colour = x.Colour, Size = x.Size, ImageUrl = x.ImageUrl,
                Notes = x.Notes, Favourite = x.Favourite, UpdatedAt = x.UpdatedAt,
                IsDeleted = x.IsDeleted
            }).ToListAsync();

        var outfits = await db.Outfits
            .Where(x => x.UserId == UserId)
            .OrderBy(x => x.UpdatedAt)
            .Select(x => new OutfitDto
            {
                ClientId = x.ClientId, Name = x.Name, TopId = x.TopId,
                BottomId = x.BottomId, ShoesId = x.ShoesId, OuterwearId = x.OuterwearId,
                AccessoryId = x.AccessoryId, Favourite = x.Favourite,
                UpdatedAt = x.UpdatedAt, IsDeleted = x.IsDeleted
            }).ToListAsync();

        return Ok(new SyncResponse
        {
            Wardrobe = wardrobe,
            Outfits = outfits,
            ServerTime = now
        });
    }
}
