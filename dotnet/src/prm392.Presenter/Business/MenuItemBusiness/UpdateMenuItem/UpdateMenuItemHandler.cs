namespace prm392.Presenter.Business.MenuItemBusiness.UpdateMenuItem;

public record UpdateMenuItemCommand(Guid Id, string Name, string Description,
    string Category, long Price) : ICommand<UpdateMenuItemResult>;
public record UpdateMenuItemResult(bool IsSuccess);
public class UpdateMenuItemHandler(Prm392Context _db)
    : ICommandHandler<UpdateMenuItemCommand, UpdateMenuItemResult>
{
    public async Task<UpdateMenuItemResult> Handle(UpdateMenuItemCommand request, 
        CancellationToken cancellationToken)
    {
        var menuItem = await _db.MenuItems.FindAsync(request.Id, cancellationToken);
        if (menuItem is null)
            throw new Exception("Can not find menu item to update!");

        menuItem.Name = request.Name;
        menuItem.Description = request.Description;
        menuItem.Category = request.Category;
        menuItem.Price = request.Price;

        _db.Update(menuItem);
        await _db.SaveChangesAsync();
        return new UpdateMenuItemResult(true);
    }
}
