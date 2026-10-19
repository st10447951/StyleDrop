namespace StyleDrop.Api.Models;

public class User
{
    public Guid Id { get; set; }
    public string Email { get; set; } = string.Empty;
    public string PasswordHash { get; set; } = string.Empty;
    public string DisplayName { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    public ICollection<WardrobeItem> WardrobeItems { get; set; } = new List<WardrobeItem>();
    public ICollection<Outfit> Outfits { get; set; } = new List<Outfit>();
}
