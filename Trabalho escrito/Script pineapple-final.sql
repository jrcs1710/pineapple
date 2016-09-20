-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema pineapplesystems
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema pineapplesystems
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `pineapplesystems` DEFAULT CHARACTER SET latin1 ;
USE `pineapplesystems` ;

-- -----------------------------------------------------
-- Table `pineapplesystems`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`usuario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nomeUsuario` VARCHAR(255) NULL DEFAULT NULL,
  `senha` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_598mvcct5levbbtcaeu7bfy6j` (`nomeUsuario` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`funcionario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cargo` INT(11) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `status` BIT(1) NOT NULL,
  `usuario` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_hujfe9giwd0dwuktb8toq7op7` (`email` ASC),
  INDEX `FK_lo1ied4ki5nhbns0qy5fdnc5r` (`usuario` ASC),
  CONSTRAINT `FK_lo1ied4ki5nhbns0qy5fdnc5r`
    FOREIGN KEY (`usuario`)
    REFERENCES `pineapplesystems`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`ambiente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`ambiente` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `andar` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `status` BIT(1) NOT NULL,
  `responsavel` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_6ojyx5xjo8d6m62o6025c4cs8` (`nome` ASC),
  INDEX `FK_53fpwvjd3lij4m2ypwecndwmh` (`responsavel` ASC),
  CONSTRAINT `FK_53fpwvjd3lij4m2ypwecndwmh`
    FOREIGN KEY (`responsavel`)
    REFERENCES `pineapplesystems`.`funcionario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`auditoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`auditoria` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dtFim` DATETIME NULL DEFAULT NULL,
  `dtInicio` DATETIME NULL DEFAULT NULL,
  `nrAuditoria` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`tipo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`tipo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_6crshu40uha0od8kny1cd807y` (`descricao` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`modelo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`modelo` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `foto` MEDIUMBLOB NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `status` BIT(1) NOT NULL,
  `tipo` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_co3vjgkjauexyd90x0b7r1dd1` (`nome` ASC),
  INDEX `FK_fknr40pxgl458sky381fjo4kl` (`tipo` ASC),
  CONSTRAINT `FK_fknr40pxgl458sky381fjo4kl`
    FOREIGN KEY (`tipo`)
    REFERENCES `pineapplesystems`.`tipo` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`patrimonio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`patrimonio` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cdPatrimonio` VARCHAR(255) NULL DEFAULT NULL,
  `descricao` VARCHAR(255) NULL DEFAULT NULL,
  `dtEntrada` DATETIME NULL DEFAULT NULL,
  `dtSaida` DATETIME NULL DEFAULT NULL,
  `ambiente` BIGINT(20) NULL DEFAULT NULL,
  `modelo` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_7rkwegyt42tcq37txy6vner0n` (`cdPatrimonio` ASC),
  INDEX `FK_ra347vxyiturk3qnown6xhct1` (`ambiente` ASC),
  INDEX `FK_aeaffqpmu1gbey34rjnudj10l` (`modelo` ASC),
  CONSTRAINT `FK_aeaffqpmu1gbey34rjnudj10l`
    FOREIGN KEY (`modelo`)
    REFERENCES `pineapplesystems`.`modelo` (`id`),
  CONSTRAINT `FK_ra347vxyiturk3qnown6xhct1`
    FOREIGN KEY (`ambiente`)
    REFERENCES `pineapplesystems`.`ambiente` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`auditoria_patrimonio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`auditoria_patrimonio` (
  `Aud_id` BIGINT(20) NOT NULL,
  `Pat_id` BIGINT(20) NOT NULL,
  INDEX `FK_etfnrn7uhsus3aqoijns63e74` (`Pat_id` ASC),
  INDEX `FK_2af2jro868ixh11ncyaqbva7l` (`Aud_id` ASC),
  CONSTRAINT `FK_2af2jro868ixh11ncyaqbva7l`
    FOREIGN KEY (`Aud_id`)
    REFERENCES `pineapplesystems`.`auditoria` (`id`),
  CONSTRAINT `FK_etfnrn7uhsus3aqoijns63e74`
    FOREIGN KEY (`Pat_id`)
    REFERENCES `pineapplesystems`.`patrimonio` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`conferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`conferencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dtFim` DATETIME NULL DEFAULT NULL,
  `dtInicio` DATETIME NULL DEFAULT NULL,
  `status` BIT(1) NOT NULL,
  `ambiente` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_p45kbryrp5hej5ygnov142u5r` (`ambiente` ASC),
  CONSTRAINT `FK_p45kbryrp5hej5ygnov142u5r`
    FOREIGN KEY (`ambiente`)
    REFERENCES `pineapplesystems`.`ambiente` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`conferencia_patrimonio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`conferencia_patrimonio` (
  `Conf_id` BIGINT(20) NOT NULL,
  `Pat_id` BIGINT(20) NOT NULL,
  INDEX `FK_6asdh2cv1nhr3h4scp3temfse` (`Pat_id` ASC),
  INDEX `FK_6d6gassfqlhuxu91f20qyvmri` (`Conf_id` ASC),
  CONSTRAINT `FK_6asdh2cv1nhr3h4scp3temfse`
    FOREIGN KEY (`Pat_id`)
    REFERENCES `pineapplesystems`.`patrimonio` (`id`),
  CONSTRAINT `FK_6d6gassfqlhuxu91f20qyvmri`
    FOREIGN KEY (`Conf_id`)
    REFERENCES `pineapplesystems`.`conferencia` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`conferenciageral`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`conferenciageral` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dtFim` DATETIME NULL DEFAULT NULL,
  `dtInicio` DATETIME NULL DEFAULT NULL,
  `nrConferencia` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`conferenciageral_conferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`conferenciageral_conferencia` (
  `ConfGer_id` BIGINT(20) NOT NULL,
  `Conf_id` BIGINT(20) NOT NULL,
  INDEX `FK_s3ksf529kus5xbyd9c2ep3ynx` (`Conf_id` ASC),
  INDEX `FK_7lo8cdse6ypqnvf6u4hjxnkau` (`ConfGer_id` ASC),
  CONSTRAINT `FK_7lo8cdse6ypqnvf6u4hjxnkau`
    FOREIGN KEY (`ConfGer_id`)
    REFERENCES `pineapplesystems`.`conferenciageral` (`id`),
  CONSTRAINT `FK_s3ksf529kus5xbyd9c2ep3ynx`
    FOREIGN KEY (`Conf_id`)
    REFERENCES `pineapplesystems`.`conferencia` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`empresa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bairro` VARCHAR(255) NULL DEFAULT NULL,
  `cidade` VARCHAR(255) NULL DEFAULT NULL,
  `cnpj` VARCHAR(255) NULL DEFAULT NULL,
  `estado` VARCHAR(255) NULL DEFAULT NULL,
  `logotipo` MEDIUMBLOB NULL DEFAULT NULL,
  `mascara` VARCHAR(255) NULL DEFAULT NULL,
  `nome` VARCHAR(255) NULL DEFAULT NULL,
  `numero` VARCHAR(255) NULL DEFAULT NULL,
  `rua` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`inconsistencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`inconsistencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `conferencia_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_snfd4tnphgfet8k7moioj93fc` (`conferencia_id` ASC),
  CONSTRAINT `FK_snfd4tnphgfet8k7moioj93fc`
    FOREIGN KEY (`conferencia_id`)
    REFERENCES `pineapplesystems`.`conferencia` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`iteminconsistencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`iteminconsistencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `status` BIT(1) NOT NULL,
  `tipoInconsistencia` INT(11) NULL DEFAULT NULL,
  `patrimonio_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_2kj1katen1tfeyuj3biykf7k3` (`patrimonio_id` ASC),
  CONSTRAINT `FK_2kj1katen1tfeyuj3biykf7k3`
    FOREIGN KEY (`patrimonio_id`)
    REFERENCES `pineapplesystems`.`patrimonio` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 103
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`inconsistencia_iteminconsistencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`inconsistencia_iteminconsistencia` (
  `Incon_id` BIGINT(20) NOT NULL,
  `ItemIncon_id` BIGINT(20) NOT NULL,
  INDEX `FK_tp2aie3pwe75jc659kdh6q52e` (`ItemIncon_id` ASC),
  INDEX `FK_5e5hf0uiaah9rceu6fxxvhsl1` (`Incon_id` ASC),
  CONSTRAINT `FK_5e5hf0uiaah9rceu6fxxvhsl1`
    FOREIGN KEY (`Incon_id`)
    REFERENCES `pineapplesystems`.`inconsistencia` (`id`),
  CONSTRAINT `FK_tp2aie3pwe75jc659kdh6q52e`
    FOREIGN KEY (`ItemIncon_id`)
    REFERENCES `pineapplesystems`.`iteminconsistencia` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`itemtransferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`itemtransferencia` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `status` BIT(1) NOT NULL,
  `tipoMovimentacao` INT(11) NULL DEFAULT NULL,
  `patrimonio_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bnyyh3irq452uumkm4iyidil9` (`patrimonio_id` ASC),
  CONSTRAINT `FK_bnyyh3irq452uumkm4iyidil9`
    FOREIGN KEY (`patrimonio_id`)
    REFERENCES `pineapplesystems`.`patrimonio` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`movimentacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`movimentacao` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `dataAprovacao` DATETIME NULL DEFAULT NULL,
  `dtSolicitacao` DATETIME NULL DEFAULT NULL,
  `obsAprovador` VARCHAR(255) NULL DEFAULT NULL,
  `obsSolicitante` VARCHAR(255) NULL DEFAULT NULL,
  `status` INT(11) NULL DEFAULT NULL,
  `atual_id` BIGINT(20) NULL DEFAULT NULL,
  `avaliador_id` BIGINT(20) NULL DEFAULT NULL,
  `destino_id` BIGINT(20) NULL DEFAULT NULL,
  `solicitante_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bm18ffuuur5a5aav10juh2ifa` (`atual_id` ASC),
  INDEX `FK_7ighr11ycxdji13t3jidtgrla` (`avaliador_id` ASC),
  INDEX `FK_rqb8qebha9y3k33ksjf7gm7n8` (`destino_id` ASC),
  INDEX `FK_q2gcxpjkq53h8wu1e5h2rsyfp` (`solicitante_id` ASC),
  CONSTRAINT `FK_7ighr11ycxdji13t3jidtgrla`
    FOREIGN KEY (`avaliador_id`)
    REFERENCES `pineapplesystems`.`funcionario` (`id`),
  CONSTRAINT `FK_bm18ffuuur5a5aav10juh2ifa`
    FOREIGN KEY (`atual_id`)
    REFERENCES `pineapplesystems`.`ambiente` (`id`),
  CONSTRAINT `FK_q2gcxpjkq53h8wu1e5h2rsyfp`
    FOREIGN KEY (`solicitante_id`)
    REFERENCES `pineapplesystems`.`funcionario` (`id`),
  CONSTRAINT `FK_rqb8qebha9y3k33ksjf7gm7n8`
    FOREIGN KEY (`destino_id`)
    REFERENCES `pineapplesystems`.`ambiente` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `pineapplesystems`.`movimentacao_itemtransferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pineapplesystems`.`movimentacao_itemtransferencia` (
  `Mov_id` BIGINT(20) NOT NULL,
  `Item_id` BIGINT(20) NOT NULL,
  INDEX `FK_tikiuksi3csaebkp3a036jnci` (`Item_id` ASC),
  INDEX `FK_slsbasqjitkbt6yo0xm5vcwo1` (`Mov_id` ASC),
  CONSTRAINT `FK_slsbasqjitkbt6yo0xm5vcwo1`
    FOREIGN KEY (`Mov_id`)
    REFERENCES `pineapplesystems`.`movimentacao` (`id`),
  CONSTRAINT `FK_tikiuksi3csaebkp3a036jnci`
    FOREIGN KEY (`Item_id`)
    REFERENCES `pineapplesystems`.`itemtransferencia` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


INSERT INTO usuario VALUES
(1, "admin", "admin");

INSERT INTO funcionario VALUES
(1, 1, null, null, 1, 1);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
