namespace prm392.Presenter.Business.ReservationBusiness.GetReservations;

public record GetReservationsQuery() : IQuery<GetReservationsResult>;
public record GetReservationsResult(IEnumerable<Reservation> Reservations);
public class GetReservationsHandler(Prm392Context _db)
    : IQueryHandler<GetReservationsQuery, GetReservationsResult>
{
    public async Task<GetReservationsResult> Handle(GetReservationsQuery request, 
        CancellationToken cancellationToken)
    {
        var reservations = await _db.Reservations.ToListAsync(cancellationToken);
        return new GetReservationsResult(reservations);
    }
}
