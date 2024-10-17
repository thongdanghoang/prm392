using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class Seat
{
    public Guid Id { get; set; }

    public string Name { get; set; } = null!;

    /// <summary>
    /// (e.g., window, booth, outdoor)
    /// </summary>
    public string? Type { get; set; }

    public short Capacity { get; set; }

    public short FloorNumber { get; set; }

    public virtual ICollection<Reservation> Reservations { get; set; } = new List<Reservation>();
}
