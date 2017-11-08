ALTER TABLE `cns`.`gather_info`
ADD CONSTRAINT `fk_contract` FOREIGN KEY (`contract_id`) REFERENCES `cns`.`contract` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `cns`.`contract`
ADD CONSTRAINT `fk_customer` FOREIGN KEY (`customer_id`) REFERENCES `cns`.`customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
