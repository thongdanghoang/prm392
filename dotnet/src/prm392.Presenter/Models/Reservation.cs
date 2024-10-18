using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class Reservation
{
    public Guid Id { get; set; }

    public int Version { get; set; }

    public Guid TransactionId { get; set; }

    public Guid SeatId { get; set; }

    public Guid UserId { get; set; }

    public DateOnly ReservationDate { get; set; }

    public TimeOnly TimeSlotFromInclusive { get; set; }

    public TimeOnly TimeSlotToExclusive { get; set; }

    /// <summary>
    /// (e.g., confirmed, cancelled)
    /// </summary>
    public string Status { get; set; } = null!;

    public short NumberOfGuests { get; set; }

    public DateTime CreatedDate { get; set; }

    public string CreatedBy { get; set; } = null!;

    public DateTime LastModifiedDate { get; set; }

    public string LastModifiedBy { get; set; } = null!;

    public virtual ReservationMenuItem? ReservationMenuItem { get; set; }

    public virtual Seat Seat { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
