namespace prm392.Presenter.Business.ReservationBusiness.GetReservationById;
public record GetReservationByIdQuery(Guid Id) : IQuery<GetReservationByIdResult>;
public record GetReservationByIdResult(Reservation Reservation);
public class GetReservationByIdHandler(Prm392Context _db)
    : IQueryHandler<GetReservationByIdQuery, GetReservationByIdResult>
{
    public async Task<GetReservationByIdResult> Handle(GetReservationByIdQuery request, 
        CancellationToken cancellationToken)
    {
        var reservation = await _db.Reservations.FindAsync(request.Id);

        if (reservation is null)
            throw new Exception($"Can not find reservation with {request.Id}");
        
        return new GetReservationByIdResult(reservation);
    }
}
