namespace prm392.Presenter.Business.SeatBusiness.GetSeatById;
public record GetSeatByIdQuery(Guid Id) : IQuery<GetSeatByIdResult>;
public record GetSeatByIdResult(Seat Seat);
public class GetSeatByIdHandler(Prm392Context _db)
    : IQueryHandler<GetSeatByIdQuery, GetSeatByIdResult>
{
    public async Task<GetSeatByIdResult> Handle(GetSeatByIdQuery request, CancellationToken cancellationToken)
    {
        var seat = await _db.Seats.FindAsync(request.Id, cancellationToken);
        if (seat is null)
            throw new Exception($"Can not find seat with {request.Id}");
        return new GetSeatByIdResult(seat);
    }
}
