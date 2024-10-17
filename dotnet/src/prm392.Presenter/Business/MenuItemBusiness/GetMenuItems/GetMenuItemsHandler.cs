namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItems;

public record GetMenuItemsQuery() : IQuery<GetMenuItemsResult>;
public record GetMenuItemsResult(IEnumerable<MenuItem> MenuItems);
public class GetMenuItemsHandler(Prm392Context _db)
    : IQueryHandler<GetMenuItemsQuery, GetMenuItemsResult>
{
    public async Task<GetMenuItemsResult> Handle(GetMenuItemsQuery query, 
        CancellationToken cancellationToken)
    {
        var menuItems = await _db.MenuItems.ToListAsync(cancellationToken);
        return new GetMenuItemsResult(menuItems);
    }
}
