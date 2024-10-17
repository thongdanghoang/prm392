using prm392.Presenter.Business.MenuItemBusiness.CreateMenuItem;

namespace prm392.Presenter.Business.ReservationBusiness.GetReservations;

public record GetReservationsResponse(IEnumerable<Reservation> Reservations);
public class GetReservationsEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("Reservations", async (ISender sender) =>
        {
            var result = await sender.Send(new GetReservationsQuery());

            var response = result.Adapt<GetReservationsResponse>();

            return response;
        })
        .WithName("GetReservations")
        .Produces<GetReservationsResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get reservations")
        .WithDescription("Get reservations");
    }
}
