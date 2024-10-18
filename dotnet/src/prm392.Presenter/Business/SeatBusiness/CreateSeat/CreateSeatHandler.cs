namespace prm392.Presenter.Business.SeatBusiness.CreateSeat;

public record CreateSeatCommand(Guid Id, string Name, string Type, short Capacity, short FloorNumber)
    : ICommand<CreateSeatResult>;
public record CreateSeatResult(Guid Id);
public class CreateSeatHandler(Prm392Context _db)
    : ICommandHandler<CreateSeatCommand, CreateSeatResult>
{
    public async Task<CreateSeatResult> Handle(CreateSeatCommand request, CancellationToken cancellationToken)
    {
        var seat = await _db.Seats.FindAsync(request.Id);
        if (seat != null)
            throw new Exception("Seat already exist!");
        var seatNew = new Seat
        {
            Id = request.Id,
            Name = request.Name,
            Type = request.Type,
            Capacity = request.Capacity,
            FloorNumber = request.FloorNumber
        };
        _db.Seats.Add(seatNew);
        await _db.SaveChangesAsync();
        return new CreateSeatResult(seatNew.Id);
    }
}
