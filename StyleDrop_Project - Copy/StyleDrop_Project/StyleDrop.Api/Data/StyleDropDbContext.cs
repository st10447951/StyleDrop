using Microsoft.EntityFrameworkCore;
using StyleDrop.Api.Models;

namespace StyleDrop.Api.Data;

public class StyleDropDbContext(DbContextOptions<StyleDropDbContext> options) : DbContext(options)
{
    public DbSet<User> Users => Set<User>();
    public DbSet<WardrobeItem> WardrobeItems => Set<WardrobeItem>();
    public DbSet<Outfit> Outfits => Set<Outfit>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>().HasIndex(x => x.Email).IsUnique();
        modelBuilder.Entity<WardrobeItem>().HasIndex(x => new { x.UserId, x.ClientId }).IsUnique();
        modelBuilder.Entity<Outfit>().HasIndex(x => new { x.UserId, x.ClientId }).IsUnique();

        modelBuilder.Entity<User>()
            .HasMany(x => x.WardrobeItems)
            .WithOne(x => x.User)
            .HasForeignKey(x => x.UserId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<User>()
            .HasMany(x => x.Outfits)
            .WithOne(x => x.User)
            .HasForeignKey(x => x.UserId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}
