namespace prm392.Presenter.Business.SeatBusiness.DeleteSeat;

public record DeleteSeatResponse(bool IsSuccess);
public class DeleteSeatEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapDelete("Seats/{Id}", async(Guid Id, ISender sender) =>
        {
            var result = await sender.Send(new DeleteSeatCommand(Id));

            var response = result.Adapt<DeleteSeatResponse>();

            return response;
        })
        .WithName("DeleteSeat")
        .Produces<DeleteSeatResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Delete Seat")
        .WithDescription("Delete Seat");
    }
}
