using prm392.Presenter.Business.MenuItemBusiness.CreateMenuItem;

namespace prm392.Presenter.Business.MenuItemBusiness.DeleteMenuItem;
public record DeleteMenuItemResponse(bool IsSuccess);
public class DeleteMenuItemEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapDelete("MenuItems/{id}", async(Guid id, ISender sender) =>
        {
            var result = await sender.Send(new DeleteMenuItemCommand(id));
            var response = result.Adapt<DeleteMenuItemResponse>();
            return response;
        })
        .WithName("DeleteMenuItem")
        .Produces<DeleteMenuItemResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Delete Menu Item")
        .WithDescription("Delete Menu Item");
    }
}
