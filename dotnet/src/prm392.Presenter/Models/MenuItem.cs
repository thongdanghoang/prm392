namespace prm392.Presenter.Models;

public partial class MenuItem
{
    public Guid Id { get; set; }

    public string Name { get; set; } = null!;

    public string? Description { get; set; }

    public long Price { get; set; }

    /// <summary>
    ///  (e.g., starter, main_course, dessert)
    /// </summary>
    public string? Category { get; set; }

    public virtual ICollection<ReservationMenuItem> ReservationMenuItems { get; set; } = new List<ReservationMenuItem>();
}
