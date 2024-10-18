namespace prm392.Presenter.Business.ReservationBusiness.UpdateReservation;

public record UpdateReservationCommand(Guid ReservationId, Guid SeatId, int Version, DateOnly ReservationDate, 
    TimeOnly TimeSlotFromInclusive, TimeOnly TimeSlotToExclusive, string Status, short NumberOfGuests, List<ReservationMenuItem> UpdatedMenuItems) 
    : ICommand<UpdateReservationResult>;
public record UpdateReservationResult(bool IsSuccess);
public class UpdateReservationHandler(Prm392Context _db)
    : ICommandHandler<UpdateReservationCommand, UpdateReservationResult>
{
    public async Task<UpdateReservationResult> Handle(UpdateReservationCommand request, 
        CancellationToken cancellationToken)
    {
        var reservation = await _db.Reservations.FindAsync(request.ReservationId);
        if (reservation == null)
        {
            throw new KeyNotFoundException("Reservation not found!");
        }

        bool isAvailable = !_db.Reservations.Any(r => r.Id != request.ReservationId &&
                                                           r.SeatId == request.SeatId &&
                                                           r.ReservationDate == request.ReservationDate &&
                                                           ((request.TimeSlotFromInclusive >= r.TimeSlotFromInclusive && request.TimeSlotFromInclusive < r.TimeSlotToExclusive) ||
                                                           (request.TimeSlotToExclusive > r.TimeSlotFromInclusive && request.TimeSlotToExclusive <= r.TimeSlotToExclusive)));
        if (!isAvailable)
        {
            throw new InvalidOperationException("Seat is not available for the selected time slot!");
        }

        reservation.SeatId = request.SeatId;
        reservation.ReservationDate = request.ReservationDate;
        reservation.Version = request.Version;
        reservation.TimeSlotFromInclusive = request.TimeSlotFromInclusive;
        reservation.TimeSlotToExclusive = request.TimeSlotToExclusive;
        reservation.Status = request.Status;
        reservation.NumberOfGuests = request.NumberOfGuests;
        reservation.LastModifiedDate = DateTime.Now;
        reservation.LastModifiedBy = "Staff";

        var existingMenuItems = _db.ReservationMenuItems.Where(r 
            => r.ReservationId == request.ReservationId).ToList();
        _db.ReservationMenuItems.RemoveRange(existingMenuItems);

        foreach (var menuItem in request.UpdatedMenuItems)
        {
            menuItem.ReservationId = reservation.Id;
            _db.ReservationMenuItems.Add(menuItem);
        }

        await _db.SaveChangesAsync();
        return new UpdateReservationResult(true);
    }
}
