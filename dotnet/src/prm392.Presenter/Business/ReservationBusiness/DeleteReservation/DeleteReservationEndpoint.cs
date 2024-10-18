namespace prm392.Presenter.Business.ReservationBusiness.DeleteReservation;

public record DeleteReservationResponse(bool IsSuccess);
public class DeleteReservationEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapDelete("Reservations/{Id}", async (Guid Id, ISender sender) =>
        {
            var result = await sender.Send(new DeleteReservationCommand(Id));
            
            var response = result.Adapt<DeleteReservationResponse>();

            return response;
        })
        .WithName("DeleteReservation")
        .Produces<DeleteReservationResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Delete Reservation")
        .WithDescription("Delete Reservation");
    }
}
