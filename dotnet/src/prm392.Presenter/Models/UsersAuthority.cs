using System;
using System.Collections.Generic;

namespace prm392.Presenter.Models;

public partial class UsersAuthority
{
    public Guid UserId { get; set; }

    public Guid AuthorityId { get; set; }

    public virtual Authority Authority { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
