using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class UserNotificationPreference
{
    public Guid Id { get; set; }

    public int Version { get; set; }

    public Guid UserId { get; set; }

    public bool Enabled { get; set; }

    public string Frequency { get; set; } = null!;

    public string NotificationType { get; set; } = null!;

    public virtual Notification User { get; set; } = null!;
}
