namespace StyleDrop.Api.Models;

public class Outfit
{
    public Guid Id { get; set; }
    public Guid UserId { get; set; }
    public User User { get; set; } = null!;
    public Guid ClientId { get; set; }
    public string Name { get; set; } = string.Empty;
    public Guid? TopId { get; set; }
    public Guid? BottomId { get; set; }
    public Guid? ShoesId { get; set; }
    public Guid? OuterwearId { get; set; }
    public Guid? AccessoryId { get; set; }
    public bool Favourite { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public bool IsDeleted { get; set; }
}
