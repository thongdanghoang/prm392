namespace prm392.Presenter.Business.ReservationBusiness.CreateReservation;

public record CreateReservationCommand
    (Guid UserId, Guid SeatId, int Version, DateOnly ReservationDate, TimeOnly TimeSlotFromInclusive, TimeOnly TimeSlotToExclusive,
    string Status, short NumberOfGuests, List<ReservationMenuItem> MenuItems)
    : ICommand<CreateReservationResult>;

public record CreateReservationResult(Guid Id);

public class CreateReservationHandler(Prm392Context _db)
    : ICommandHandler<CreateReservationCommand, CreateReservationResult>
{
    public async Task<CreateReservationResult> Handle(CreateReservationCommand command,
        CancellationToken cancellationToken)
    {
        var seat = await _db.Seats.FindAsync(command.SeatId);
        if (seat == null || seat.Capacity < command.NumberOfGuests)
            throw new Exception("Seat not available or capacity exceeded!");

        bool isAvailable = !_db.Reservations.Any(r => r.SeatId == command.SeatId &&
                                                           r.ReservationDate == command.ReservationDate &&
                                                           (command.TimeSlotFromInclusive >= r.TimeSlotFromInclusive && command.TimeSlotFromInclusive < r.TimeSlotToExclusive ||
                                                           command.TimeSlotToExclusive > r.TimeSlotFromInclusive && command.TimeSlotToExclusive <= r.TimeSlotToExclusive));
        if (!isAvailable)
            throw new Exception("Seat is not available for the selected time slot!");

        var reservation = new Reservation
        {
            Id = Guid.NewGuid(),
            UserId = command.UserId,
            SeatId = command.SeatId,
            Version = command.Version,
            ReservationDate = command.ReservationDate,
            TimeSlotFromInclusive = command.TimeSlotFromInclusive,
            TimeSlotToExclusive = command.TimeSlotToExclusive,
            Status = command.Status,
            NumberOfGuests = command.NumberOfGuests,
            CreatedDate = DateTime.Now,
            CreatedBy = "Staff",
            LastModifiedDate = DateTime.Now,
            LastModifiedBy = "Staff",

        };

        _db.Reservations.Add(reservation);
        await _db.SaveChangesAsync(cancellationToken);

        foreach (var menuItem in command.MenuItems)
        {
            menuItem.ReservationId = reservation.Id;
            _db.ReservationMenuItems.Add(menuItem);
        }

        await _db.SaveChangesAsync();
        return new CreateReservationResult(reservation.Id);
    }
}
