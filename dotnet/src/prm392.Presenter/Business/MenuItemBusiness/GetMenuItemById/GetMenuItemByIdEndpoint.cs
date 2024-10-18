namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItemById;
public record GetMenuItemByIdResponse(MenuItem MenuItem);
public class GetMenuItemByIdEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("MenuItems/{Id}", async (Guid Id, ISender sender) =>
        {
            var result = await sender.Send(new GetMenuItemByIdQuery(Id));
            var response = result.Adapt<GetMenuItemByIdResponse>();
            return response;
        })
        .WithName("GetMenuItemById")
        .Produces<GetMenuItemByIdResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get Menu Item By Id")
        .WithDescription("Get Menu Item By Id");
    }
}
