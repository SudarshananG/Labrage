package com.deadshot.labrage_v3.TableOtpGenerator;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OtpGeneratorRepository extends CrudRepository<OtpGenerator, Long>{
	List<OtpGenerator> findAll();
}
