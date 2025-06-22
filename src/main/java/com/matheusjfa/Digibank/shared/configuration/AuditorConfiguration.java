package com.matheusjfa.Digibank.shared.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component("auditorAware")
public class AuditorConfiguration implements AuditorAware<UUID> {
	@Override
	public Optional<UUID> getCurrentAuditor() {
		return Optional.of(UUID.randomUUID());
	}
}
