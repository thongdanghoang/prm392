namespace prm392.Presenter.Business.MenuItemBusiness.CreateMenuItem;

public record CreateMenuItemCommand(Guid Id, string Name, string Description, 
    string Category, long Price) : ICommand<CreateMenuItemResult>;
public record CreateMenuItemResult(Guid Id);
public class CreateMenuItemHandler(Prm392Context _db) 
    : ICommandHandler<CreateMenuItemCommand, CreateMenuItemResult>
{
    public async Task<CreateMenuItemResult> Handle(CreateMenuItemCommand command, CancellationToken cancellationToken)
    {
        var exist = await _db.MenuItems.FindAsync(command.Id);
        if (exist != null)
            throw new Exception("Menu item already exists!");
        var menuItem = new MenuItem
        {
            Id = command.Id,
            Name = command.Name,
            Description = command.Description,
            Category = command.Category,
            Price = command.Price
        };
        _db.MenuItems.Add(menuItem);
        await _db.SaveChangesAsync();
        return new CreateMenuItemResult(menuItem.Id);
    }
}
