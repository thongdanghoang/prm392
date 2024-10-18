namespace prm392.Presenter.Business.SeatBusiness.GetSeats;
public record GetSeatsResponse(IEnumerable<Seat> Seats);

public class GetSeatsEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("Seats", async (ISender sender) =>
        {
            var result = await sender.Send(new GetSeatsQuery());
            var response = result.Adapt<GetSeatsResponse>();
            return response;
        })
        .WithName("GetSeats")
        .Produces<GetSeatsResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get Seats")
        .WithDescription("Get Seats");
    }
}
