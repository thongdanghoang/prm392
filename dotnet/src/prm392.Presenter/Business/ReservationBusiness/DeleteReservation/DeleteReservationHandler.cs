namespace prm392.Presenter.Business.ReservationBusiness.DeleteReservation;

public record DeleteReservationCommand(Guid Id) : ICommand<DeleteReservationResult>;
public record DeleteReservationResult(bool IsSuccess);
public class DeleteReservationHandler(Prm392Context _db)
    : ICommandHandler<DeleteReservationCommand, DeleteReservationResult>
{
    public async Task<DeleteReservationResult> Handle(DeleteReservationCommand request, 
        CancellationToken cancellationToken)
    {
        var exist = await _db.Reservations.FindAsync(request.Id);

        if (exist is null)
            throw new Exception("Can not find reservation to delete!");

        var menuItems = _db.ReservationMenuItems
            .Where(mi => mi.ReservationId == request.Id)
            .ToList();

        _db.ReservationMenuItems.RemoveRange(menuItems);
        await _db.SaveChangesAsync(cancellationToken);

        var reservation = await _db.Reservations.FindAsync(request.Id);
        if (reservation != null)
        {
            _db.Reservations.Remove(reservation);
            await _db.SaveChangesAsync();
        }
        return new DeleteReservationResult(true);
    }
}
