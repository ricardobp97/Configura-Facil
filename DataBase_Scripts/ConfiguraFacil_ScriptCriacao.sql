-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ConfiguraFacil` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ConfiguraFacil` DEFAULT CHARACTER SET utf8 ;
USE `ConfiguraFacil` ;

-- -----------------------------------------------------
-- Table `mydb`.`Pacote`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`Pacote` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`Pacote` (
  `idPacote` INT NOT NULL,
  `Designacao` VARCHAR(45) NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Preco` DECIMAL NOT NULL,
  PRIMARY KEY (`idPacote`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Componente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`Componente` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`Componente` (
  `idComponente` INT NOT NULL,
  `Designacao` VARCHAR(45) NOT NULL,
  `Preco` DECIMAL NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Stock` INT NOT NULL,
  `Pacote_id` INT NULL,
  PRIMARY KEY (`idComponente`),
  INDEX `fk_Componente_Pacote1_idx` (`Pacote_id` ASC),
  CONSTRAINT `fk_Componente_Pacote1`
    FOREIGN KEY (`Pacote_id`)
    REFERENCES `ConfiguraFacil`.`Pacote` (`idPacote`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ComponenteObrigatorio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`ComponenteObrigatorio` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`ComponenteObrigatorio` (
  `idCompObrig` INT NOT NULL,
  `Componente_id` INT NOT NULL,
  PRIMARY KEY (`idCompObrig`, `Componente_id`),
  INDEX `fk_ComponenteObrigatorio_Componente1_idx` (`Componente_id` ASC),
  CONSTRAINT `fk_ComponenteObrigatorio_Componente1`
    FOREIGN KEY (`Componente_id`)
    REFERENCES `ConfiguraFacil`.`Componente` (`idComponente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ComponenteIncompativel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`ComponenteIncompativel` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`ComponenteIncompativel` (
  `idCompIncomp` INT NOT NULL,
  `Componente_idComponente` INT NOT NULL,
  PRIMARY KEY (`idCompIncomp`, `Componente_idComponente`),
  INDEX `fk_ComponenteIncompativel_Componente1_idx` (`Componente_idComponente` ASC),
  CONSTRAINT `fk_ComponenteIncompativel_Componente1`
    FOREIGN KEY (`Componente_idComponente`)
    REFERENCES `ConfiguraFacil`.`Componente` (`idComponente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Configuracao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`Configuracao` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`Configuracao` (
  `idConfiguracao` INT NOT NULL,
  `NIF_Cliente` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idConfiguracao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`Funcionario` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`Funcionario` (
  `Username` VARCHAR(10) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ConfigPack`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`ConfigPack` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`ConfigPack` (
  `Configuracao_id` INT NOT NULL,
  `Pacote_id` INT NOT NULL,
  INDEX `fk_ConfigPack_Configuracao1_idx` (`Configuracao_id` ASC),
  INDEX `fk_ConfigPack_Pacote1_idx` (`Pacote_id` ASC),
  PRIMARY KEY (`Configuracao_id`, `Pacote_id`),
  CONSTRAINT `fk_ConfigPack_Configuracao1`
    FOREIGN KEY (`Configuracao_id`)
    REFERENCES `ConfiguraFacil`.`Configuracao` (`idConfiguracao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ConfigPack_Pacote1`
    FOREIGN KEY (`Pacote_id`)
    REFERENCES `ConfiguraFacil`.`Pacote` (`idPacote`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ConfigComp`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ConfiguraFacil`.`ConfigComp` ;

CREATE TABLE IF NOT EXISTS `ConfiguraFacil`.`ConfigComp` (
  `Configuracao_id` INT NOT NULL,
  `Componente_id` INT NOT NULL,
  INDEX `fk_ConfigComp_Configuracao1_idx` (`Configuracao_id` ASC),
  INDEX `fk_ConfigComp_Componente1_idx` (`Componente_id` ASC),
  PRIMARY KEY (`Configuracao_id`, `Componente_id`),
  CONSTRAINT `fk_ConfigComp_Configuracao1`
    FOREIGN KEY (`Configuracao_id`)
    REFERENCES `ConfiguraFacil`.`Configuracao` (`idConfiguracao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ConfigComp_Componente1`
    FOREIGN KEY (`Componente_id`)
    REFERENCES `ConfiguraFacil`.`Componente` (`idComponente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
