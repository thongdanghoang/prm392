namespace prm392.Presenter.Business.ReservationBusiness.UpdateReservation;

public record UpdateReservationRequest(Guid ReservationId, Guid SeatId, DateOnly ReservationDate,
    TimeOnly FromTime, TimeOnly ToTime, string Status, short NumberOfGuests, List<ReservationMenuItem> UpdatedMenuItems);
public record UpdateReservationResponse(bool IsSuccess);
public class UpdateReservationEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPut("Reservations", async (UpdateReservationRequest request, ISender sender) =>
        {
            var command = request.Adapt<UpdateReservationCommand>();

            var result = await sender.Send(command);

            var response = result.Adapt<UpdateReservationResponse>();

            return response;
        })
        .WithName("UpdateReservation")
        .Produces<UpdateReservationResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Update Reservation")
        .WithDescription("Update Reservation");
    }
}
