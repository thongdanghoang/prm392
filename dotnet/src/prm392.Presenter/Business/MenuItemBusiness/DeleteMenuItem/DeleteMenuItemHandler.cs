namespace prm392.Presenter.Business.MenuItemBusiness.DeleteMenuItem;

public record DeleteMenuItemCommand(Guid Id) : ICommand<DeleteMenuItemResult>;
public record DeleteMenuItemResult(bool IsSuccess);
public class DeleteMenuItemHandler(Prm392Context _db)
    : ICommandHandler<DeleteMenuItemCommand, DeleteMenuItemResult>
{
    public async Task<DeleteMenuItemResult> Handle(DeleteMenuItemCommand request, 
        CancellationToken cancellationToken)
    {
        var menuItem = await _db.MenuItems.FindAsync(request.Id);
        if (menuItem == null)
            throw new Exception("Can not find menu item to delete!");
        _db.MenuItems.Remove(menuItem);
        await _db.SaveChangesAsync();
        return new DeleteMenuItemResult(true);
    }
}
