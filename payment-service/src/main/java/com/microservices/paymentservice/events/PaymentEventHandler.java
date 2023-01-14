package com.microservices.paymentservice.events;

import com.microservices.commons.events.PaymentCancelledEvent;
import com.microservices.commons.events.PaymentProcessedEvent;
import com.microservices.paymentservice.data.Payment;
import com.microservices.paymentservice.data.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentEventHandler {
  private final PaymentRepository paymentRepository;

  @EventHandler
  public void on(PaymentProcessedEvent event) {
    var payment = new Payment();
    BeanUtils.copyProperties(event, payment);
    payment.setPaymentStatus("COMPLETED");
    payment.setTimeStamp(new Date());
    paymentRepository.save(payment);
  }

  @EventHandler
  public void on(PaymentCancelledEvent event) {
    var opt = paymentRepository.findById(event.paymentId());
    if (opt.isEmpty()) {
      return;
    }
    var payment = opt.get();
    payment.setPaymentStatus(event.paymentStatus());
    paymentRepository.save(payment);
  }
}
