namespace prm392.Presenter.Business.MenuItemBusiness.CreateMenuItem;

public record CreateMenuItemRequest(Guid Id, string Name, string Description,
    string Category, long Price);
public record CreateMenuItemResponse(Guid Id);
public class CreateMenuItemEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPost("MenuItems", async (CreateMenuItemRequest request, ISender sender) =>
        {
            var command = request.Adapt<CreateMenuItemCommand>();

            var result = await sender.Send(command);

            var response = result.Adapt<CreateMenuItemResponse>();

            return response;
        })
        .WithName("CreateMenuItem")
            .Produces<CreateMenuItemResponse>(StatusCodes.Status201Created)
            .ProducesProblem(StatusCodes.Status400BadRequest)
            .WithSummary("Create MenuItems")
            .WithDescription("Create MenuItems");
    }
}
