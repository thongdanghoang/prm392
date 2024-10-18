namespace prm392.Presenter.Business.SeatBusiness.UpdateSeat;

public record UpdateSeatRequest(Guid Id, string Name, string Type, short Capacity, short FloorNumber);
public record UpdateSeatResponse(bool IsSuccess);
public class UpdateSeatEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPut("Seats", async (UpdateSeatRequest request, ISender sender) =>
        {
            var command = request.Adapt<UpdateSeatCommand>();

            var result = await sender.Send(command);

            var response = result.Adapt<UpdateSeatResponse>();

            return response;
        })
        .WithName("UpdateSeat")
        .Produces<UpdateSeatResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Update Seat")
        .WithDescription("Update Seat");
    }
}
