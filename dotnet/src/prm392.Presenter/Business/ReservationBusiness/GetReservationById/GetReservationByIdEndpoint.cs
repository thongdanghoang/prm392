namespace prm392.Presenter.Business.ReservationBusiness.GetReservationById;
public record GetReservationByIdResponse(Reservation Reservation);
public class GetReservationByIdEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("Reservations/{id}", async (Guid id, ISender sender) =>
        {
            var result = await sender.Send(new GetReservationByIdQuery(id));
            var response = result.Adapt<GetReservationByIdResponse>();
            return response;
        })
        .WithName("GetReservationById")
        .Produces<GetReservationByIdResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get Reservation By Id")
        .WithDescription("Get Reservation By Id");
    }
}
