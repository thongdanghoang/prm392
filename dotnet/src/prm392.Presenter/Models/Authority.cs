namespace prm392.Presenter.Models;

public partial class Authority
{
    public Guid Id { get; set; }

    public string Role { get; set; } = null!;

    public virtual ICollection<UsersAuthority> UsersAuthorities { get; set; } = new List<UsersAuthority>();
}
