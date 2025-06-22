package ru.myapp.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arthur.inzhilov.grpc.stubs.HelloWorldServiceGrpc;
import ru.arthur.inzhilov.grpc.stubs.ItemRequestDto;
import ru.arthur.inzhilov.grpc.stubs.ItemResponseDto;
import ru.myapp.mappers.GrpcMapper;
import ru.myapp.mappers.ItemMapper;
import ru.myapp.persistence.model.Item;
import ru.myapp.persistence.repository.ItemRepository;

import static ru.myapp.utils.GoogleTimestampConverter.instantToTimestamp;

@Slf4j
@Service
@GrpcService
@RequiredArgsConstructor
public class GrpcItemService extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    private final ItemRepository itemRepository;
    private final GrpcMapper grpcMapper;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public void saveItem(ItemRequestDto itemRequestDto, StreamObserver<ItemResponseDto> responseObserver) {
        ru.myapp.dto.request.ItemRequestDto requestDto = grpcMapper.map(itemRequestDto);
        log.info("gRPC: Получен ItemRequestDto: {}", requestDto);

        Item item = itemRepository.save(itemMapper.toItem(requestDto));

        log.info("В бд сохранен item: {}", item);

        ItemResponseDto itemResponseDto = ItemResponseDto.newBuilder()
                .setId(item.getId())
                .setName(item.getName())
                .setDescription(item.getDescription())
                .setAmount(item.getAmount())
                .setEventTime(instantToTimestamp(item.getCreatedAt()))
                .build();

        responseObserver.onNext(itemResponseDto);
        responseObserver.onCompleted();

        log.info("gRPC: Отправлен ItemResponseDto: {}", itemMapper.toItemResponseDto(item));
    }
}
