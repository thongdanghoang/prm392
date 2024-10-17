namespace prm392.Presenter.Business.ReservationBusiness.CreateReservation;

public record CreateReservationRequest(Guid UserId, Guid SeatId, int Version, DateOnly ReservationDate,
    TimeOnly TimeSlotFromInclusive, TimeOnly TimeSlotToExclusive, string Status, short NumberOfGuests, List<ReservationMenuItem> MenuItems);
public record CreateReservationResponse(Guid Id);
public class CreateReservationEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPost("/Reservations", async (CreateReservationRequest request, ISender sender) =>
        {
            var command = request.Adapt<CreateReservationCommand>();

            var result = await sender.Send(command);

            var response = result.Adapt<CreateReservationResponse>();

            return Results.Created($"/Reservations/{response.Id}", response);
        })
        .WithName("CreateReservation")
            .Produces<CreateReservationResponse>(StatusCodes.Status201Created)
            .ProducesProblem(StatusCodes.Status400BadRequest)
            .WithSummary("Create Reservation")
            .WithDescription("Create Reservtion");
    }
}
