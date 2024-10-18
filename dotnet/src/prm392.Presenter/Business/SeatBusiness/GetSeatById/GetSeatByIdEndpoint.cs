namespace prm392.Presenter.Business.SeatBusiness.GetSeatById;

public record GetSeatByIdResponse(Seat Seat);
public class GetSeatByIdEndpoint : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        app.MapGet("Seats/{Id}", async (Guid Id, ISender sender) =>
        {
            var result = await sender.Send(new GetSeatByIdQuery(Id));

            var response = result.Adapt<GetSeatByIdResponse>();

            return response;
        })
        .WithName("GetSeatById")
        .Produces<GetSeatByIdResponse>(StatusCodes.Status200OK)
        .ProducesProblem(StatusCodes.Status400BadRequest)
        .WithSummary("Get seat by id")
        .WithDescription("Get seat by id");
    }
}
