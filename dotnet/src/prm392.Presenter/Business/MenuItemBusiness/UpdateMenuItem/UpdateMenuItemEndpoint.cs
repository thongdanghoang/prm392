namespace prm392.Presenter.Business.MenuItemBusiness.UpdateMenuItem;
public record UpdateMenuItemRequest(Guid Id, string Name, string Description,
    string Category, long Price);
public record UpdateMenuItemResponse(bool IsSuccess);
public class UpdateMenuItemEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPut("MenuItems", async (UpdateMenuItemRequest request, ISender sender) =>
        {
            var command = request.Adapt<UpdateMenuItemCommand>();

            var result = await sender.Send(command);

            var response = result.Adapt<UpdateMenuItemResponse>();

            return response;
        })
        .WithName("UpdateMenuItem")
        .Produces<UpdateMenuItemResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Update Menu Item")
        .WithDescription("Update Menu Item");
    }
}
