using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class ReservationMenuItem
{
    public Guid ReservationId { get; set; }

    public Guid MenuItemId { get; set; }

    public short Quantity { get; set; }

    public virtual MenuItem MenuItem { get; set; } = null!;

    public virtual Reservation Reservation { get; set; } = null!;
}
