using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class User
{
    public Guid Id { get; set; }

    public int Version { get; set; }

    public string Username { get; set; } = null!;

    public string Password { get; set; } = null!;

    public string? Email { get; set; }

    public string? Phone { get; set; }

    public string? FirstName { get; set; }

    public string? LastName { get; set; }

    public string? Avatar { get; set; }

    public DateOnly? Birthday { get; set; }

    public DateTime CreatedDate { get; set; }

    public string CreatedBy { get; set; } = null!;

    public DateTime LastModifiedDate { get; set; }

    public string LastModifiedBy { get; set; } = null!;

    public virtual ICollection<Notification> Notifications { get; set; } = new List<Notification>();

    public virtual ICollection<Reservation> Reservations { get; set; } = new List<Reservation>();

    public virtual UsersAuthority? UsersAuthority { get; set; }
}
