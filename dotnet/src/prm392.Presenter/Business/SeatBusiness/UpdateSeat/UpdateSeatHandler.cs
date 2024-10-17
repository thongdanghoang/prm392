namespace prm392.Presenter.Business.SeatBusiness.UpdateSeat;

public record UpdateSeatCommand(Guid Id, string Name, string Type, short Capacity, short FloorNumber)
    : ICommand<UpdateSeatResult>;
public record UpdateSeatResult(bool IsSuccess);
public class UpdateSeatHandler(Prm392Context _db)
    : ICommandHandler<UpdateSeatCommand, UpdateSeatResult>
{
    public async Task<UpdateSeatResult> Handle(UpdateSeatCommand request, CancellationToken cancellationToken)
    {
        var exist = await _db.Seats.FindAsync(request.Id, cancellationToken);
        if (exist == null)
            throw new Exception($"Can not find seat with {request.Id} to update");
        exist.Name = request.Name;
        exist.Type = request.Type;
        exist.Capacity = request.Capacity;
        exist.FloorNumber = request.FloorNumber;
        _db.Seats.Update(exist);
        await _db.SaveChangesAsync();
        return new UpdateSeatResult(true);
    }
}
