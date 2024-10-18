
namespace prm392.Presenter.Business.SeatBusiness.GetSeats;
public record GetSeatsQuery() : IQuery<GetSeatsResult>;
public record GetSeatsResult(IEnumerable<Seat> Seats);
public class GetSeatsHandler(Prm392Context _db)
    : IQueryHandler<GetSeatsQuery, GetSeatsResult>
{
    public async Task<GetSeatsResult> Handle(GetSeatsQuery request, CancellationToken cancellationToken)
    {
        var seats = await _db.Seats.ToListAsync(cancellationToken);

        return new GetSeatsResult(seats);
    }
}
