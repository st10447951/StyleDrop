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
[Route("api/wardrobe")]
public class WardrobeController(StyleDropDbContext db) : ControllerBase
{
    private Guid UserId => Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [HttpGet]
    public async Task<ActionResult<IEnumerable<WardrobeItemDto>>> GetAll() =>
        Ok(await db.WardrobeItems.Where(x => x.UserId == UserId)
            .OrderByDescending(x => x.UpdatedAt)
            .Select(x => ToDto(x)).ToListAsync());

    [HttpPost]
    public async Task<ActionResult> Create(WardrobeItemDto dto)
    {
        if (await db.WardrobeItems.AnyAsync(x => x.UserId == UserId && x.ClientId == dto.ClientId))
            return Conflict("This wardrobe item already exists.");

        db.WardrobeItems.Add(new WardrobeItem
        {
            Id = Guid.NewGuid(), UserId = UserId, ClientId = dto.ClientId,
            Name = dto.Name, Category = dto.Category, Brand = dto.Brand,
            Colour = dto.Colour, Size = dto.Size, ImageUrl = dto.ImageUrl,
            Notes = dto.Notes, Favourite = dto.Favourite,
            CreatedAt = DateTime.UtcNow, UpdatedAt = DateTime.UtcNow,
            IsDeleted = dto.IsDeleted
        });
        await db.SaveChangesAsync();
        return Ok();
    }

    [HttpPut("{clientId:guid}")]
    public async Task<ActionResult> Update(Guid clientId, WardrobeItemDto dto)
    {
        var item = await db.WardrobeItems.FirstOrDefaultAsync(x =>
            x.UserId == UserId && x.ClientId == clientId);
        if (item == null) return NotFound();

        item.Name = dto.Name; item.Category = dto.Category; item.Brand = dto.Brand;
        item.Colour = dto.Colour; item.Size = dto.Size; item.ImageUrl = dto.ImageUrl;
        item.Notes = dto.Notes; item.Favourite = dto.Favourite;
        item.IsDeleted = dto.IsDeleted; item.UpdatedAt = DateTime.UtcNow;
        await db.SaveChangesAsync();
        return Ok();
    }

    [HttpDelete("{clientId:guid}")]
    public async Task<ActionResult> Delete(Guid clientId)
    {
        var item = await db.WardrobeItems.FirstOrDefaultAsync(x =>
            x.UserId == UserId && x.ClientId == clientId);
        if (item == null) return NotFound();

        item.IsDeleted = true;
        item.UpdatedAt = DateTime.UtcNow;
        await db.SaveChangesAsync();
        return NoContent();
    }

    private static WardrobeItemDto ToDto(WardrobeItem x) => new()
    {
        ClientId = x.ClientId, Name = x.Name, Category = x.Category, Brand = x.Brand,
        Colour = x.Colour, Size = x.Size, ImageUrl = x.ImageUrl, Notes = x.Notes,
        Favourite = x.Favourite, UpdatedAt = x.UpdatedAt, IsDeleted = x.IsDeleted
    };
}
