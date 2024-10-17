using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class Notification
{
    public Guid Id { get; set; }

    public int Version { get; set; }

    public Guid UserId { get; set; }

    public virtual User User { get; set; } = null!;

    public virtual ICollection<UserNotificationPreference> UserNotificationPreferences { get; set; } = new List<UserNotificationPreference>();
}
