namespace StyleDrop.Api.DTOs;

public class WardrobeItemDto
{
    public Guid ClientId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Brand { get; set; } = string.Empty;
    public string Colour { get; set; } = string.Empty;
    public string Size { get; set; } = string.Empty;
    public string? ImageUrl { get; set; }
    public string Notes { get; set; } = string.Empty;
    public bool Favourite { get; set; }
    public DateTime UpdatedAt { get; set; }
    public bool IsDeleted { get; set; }
}

public class OutfitDto
{
    public Guid ClientId { get; set; }
    public string Name { get; set; } = string.Empty;
    public Guid? TopId { get; set; }
    public Guid? BottomId { get; set; }
    public Guid? ShoesId { get; set; }
    public Guid? OuterwearId { get; set; }
    public Guid? AccessoryId { get; set; }
    public bool Favourite { get; set; }
    public DateTime UpdatedAt { get; set; }
    public bool IsDeleted { get; set; }
}

public class SyncRequest
{
    public List<WardrobeItemDto> Wardrobe { get; set; } = [];
    public List<OutfitDto> Outfits { get; set; } = [];
}

public class SyncResponse
{
    public List<WardrobeItemDto> Wardrobe { get; set; } = [];
    public List<OutfitDto> Outfits { get; set; } = [];
    public DateTime ServerTime { get; set; }
}
