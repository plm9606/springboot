package com.example.web.comunityweb.batch.jobs.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.transaction.NoTransactionException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 큐를 사용해 저장하는 ItemReader 구현체
// ItemReader의 기본 반환 타입은 단수형인데 객체 1개씩 db에 쿼리를 요청하게 되는데 이는 비효율적
public class QueueItemReader<T> implements ItemReader {
    private Queue<T> queue;

    // 저장소에 저장될 타깃 데이터를 한번에 불러와 큐에 담아놓는다.
    public QueueItemReader(List<T> data){
        this.queue = new LinkedList<>(data);
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NoTransactionException {
        // read()메소드를 사용할 때 큐의 poll()메소드를 사용해 큐에서 데이터를 하나씩 반환한다.
        return  this.queue.poll();
    }
}
