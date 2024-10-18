namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItemByCategory;

public record GetMenuItemByCategoryQuery(string Category) : IQuery<GetMenuItemByCategoryResult>;
public record GetMenuItemByCategoryResult(IEnumerable<MenuItem> MenuItems);
public class GetMenuItemByCategoryHandler(Prm392Context _db)
    : IQueryHandler<GetMenuItemByCategoryQuery, GetMenuItemByCategoryResult>
{
    public async Task<GetMenuItemByCategoryResult> Handle(GetMenuItemByCategoryQuery request, 
        CancellationToken cancellationToken)
    {
        var menuItems = await _db.MenuItems.Where(m => m.Category.Equals(request.Category)).ToListAsync(cancellationToken);
        if (menuItems == null)
            throw new Exception("menu item do not exists!");
        return new GetMenuItemByCategoryResult(menuItems);
    }
}
