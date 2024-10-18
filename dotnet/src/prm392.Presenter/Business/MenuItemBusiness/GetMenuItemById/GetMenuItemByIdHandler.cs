
namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItemById;
public record GetMenuItemByIdQuery(Guid Id) : IQuery<GetMenuItemByIdResult>;
public record GetMenuItemByIdResult(MenuItem MenuItem);

public class GetMenuItemByIdHandler(Prm392Context _db)
    : IQueryHandler<GetMenuItemByIdQuery, GetMenuItemByIdResult>
{
    public async Task<GetMenuItemByIdResult> Handle(GetMenuItemByIdQuery request, 
        CancellationToken cancellationToken)
    {
        var menuItem = await _db.MenuItems.FindAsync(request.Id, cancellationToken);
        if (menuItem == null)
            throw new Exception("Menu item do not exists!");
        return new GetMenuItemByIdResult(menuItem);
    }
}
