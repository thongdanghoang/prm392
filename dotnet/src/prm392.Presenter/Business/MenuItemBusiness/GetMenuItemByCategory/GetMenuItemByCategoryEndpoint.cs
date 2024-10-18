namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItemByCategory;
public record GetMenuItemByCategoryResponse(IEnumerable<MenuItem> MenuItems);
public class GetMenuItemByCategoryEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("MenuItems/category/{category}", async (string category, ISender sender) =>
        {
            var result = await sender.Send(new GetMenuItemByCategoryQuery(category));
            var response = result.Adapt<GetMenuItemByCategoryResponse>();
            return response;
        })
        .WithName("GetMenuItemByCategory")
        .Produces<GetMenuItemByCategoryResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get Menu Item By Category")
        .WithDescription("Get Menu Item By Category");
    }
}
