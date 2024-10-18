
using prm392.Presenter.Business.ReservationBusiness.CreateReservation;

namespace prm392.Presenter.Business.MenuItemBusiness.GetMenuItems;
public record GetMenuItemsResponse(IEnumerable<MenuItem> MenuItems);
public class GetMenuItemsEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("MenuItems", async (ISender sender) =>
        {
            var result = await sender.Send(new GetMenuItemsQuery());
            
            var response = result.Adapt<GetMenuItemsResponse>();

            return response;
        })
        .WithName("GetMenuItems")
            .Produces<GetMenuItemsResponse>(StatusCodes.Status200OK)
            .ProducesProblem(StatusCodes.Status400BadRequest)
            .WithSummary("Get MenuItems")
            .WithDescription("Get MenuItems");
    }
}
