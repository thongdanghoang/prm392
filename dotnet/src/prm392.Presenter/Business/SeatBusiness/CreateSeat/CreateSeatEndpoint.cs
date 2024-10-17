namespace prm392.Presenter.Business.SeatBusiness.CreateSeat;

public record CreateSeatRequest(Guid Id, string Name, string Type, short Capacity, short FloorNumber);
public record CreateSeatResponse(Guid Id);
public class CreateSeatEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapPost("Seats", async(CreateSeatRequest request, ISender sender) =>
        {
            var command = request.Adapt<CreateSeatCommand>();
            var result = await sender.Send(command);
            var response = result.Adapt<CreateSeatResponse>();
            return response;
        })
        .WithName("CreateSeat")
        .Produces<CreateSeatResponse>(StatusCodes.Status201Created)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Create seat")
        .WithDescription("Create seat");
    }
}
