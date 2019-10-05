-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 15 Apr 2019 pada 07.31
-- Versi server: 10.1.34-MariaDB
-- Versi PHP: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotik`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detailpenjualan`
--

CREATE TABLE `detailpenjualan` (
  `nofaktur` varchar(255) NOT NULL,
  `idobat` varchar(255) NOT NULL,
  `hargajual` varchar(255) NOT NULL,
  `quantity` varchar(255) NOT NULL,
  `subtotal` varchar(255) NOT NULL,
  `tanggaljual` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detailpenjualan`
--

INSERT INTO `detailpenjualan` (`nofaktur`, `idobat`, `hargajual`, `quantity`, `subtotal`, `tanggaljual`) VALUES
('', '1', '7000', '3', '21000', '2019-04-15');

-- --------------------------------------------------------

--
-- Struktur dari tabel `distributor`
--

CREATE TABLE `distributor` (
  `iddistributor` int(11) NOT NULL,
  `namadistributor` varchar(255) NOT NULL,
  `jeniskelamin` varchar(255) NOT NULL,
  `nohp` int(12) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `distributor`
--

INSERT INTO `distributor` (`iddistributor`, `namadistributor`, `jeniskelamin`, `nohp`, `alamat`) VALUES
(1, 'bang alex', 'Laki-Laki', 987654321, 'padang'),
(3, 'jhondoe', 'Laki-Laki', 1234567890, 'jepang');

-- --------------------------------------------------------

--
-- Struktur dari tabel `member`
--

CREATE TABLE `member` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `jenis_kelamin` varchar(255) NOT NULL,
  `alamat` text NOT NULL,
  `level` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `member`
--

INSERT INTO `member` (`id`, `nama`, `username`, `password`, `jenis_kelamin`, `alamat`, `level`) VALUES
(1, 'erpan ', 'erpan1140', '1140', 'Laki-Laki', 'Jakarta', 'User'),
(4, 'mhd farid', 'boy', '1140', 'Laki-Laki', 'padang', 'User'),
(8, 'Muhammad Farid', 'farid', '2001', 'Laki-Laki', 'ampang gadang', 'Admin'),
(9, 'muhammad arid', 'erere', '12345', 'Laki-Laki', 'manajah', 'User'),
(10, 'jayawijaya', 'jaya', '12345', 'Laki-Laki', 'jakarta', 'User'),
(11, 'mizan', 'mizan', '12345', 'Laki-Laki', 'suliki', 'User');

-- --------------------------------------------------------

--
-- Struktur dari tabel `obat`
--

CREATE TABLE `obat` (
  `idobat` int(11) NOT NULL,
  `namaobat` varchar(20) DEFAULT NULL,
  `hargapokok` int(20) DEFAULT NULL,
  `hargajual` int(20) DEFAULT NULL,
  `stok` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `obat`
--

INSERT INTO `obat` (`idobat`, `namaobat`, `hargapokok`, `hargajual`, `stok`) VALUES
(1, 'parasetamol', 5000, 7000, 10),
(2, 'promag', 4000, 5000, 22),
(3, 'komik', 1500, 2000, 29),
(4, 'Bodrex', 2000, 3000, 11),
(5, 'inzana', 2000, 3000, 20);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE `penjualan` (
  `nofaktur` int(11) NOT NULL,
  `tanggaljual` varchar(255) NOT NULL,
  `namapelanggan` varchar(255) NOT NULL,
  `jumlahpembelian` varchar(255) NOT NULL,
  `total` varchar(255) NOT NULL,
  `bayar` varchar(255) NOT NULL,
  `kembali` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`nofaktur`, `tanggaljual`, `namapelanggan`, `jumlahpembelian`, `total`, `bayar`, `kembali`) VALUES
(321, '2019-04-15', 'mizan', '3', '21000', '21000', '0');

-- --------------------------------------------------------

--
-- Struktur dari tabel `petugas`
--

CREATE TABLE `petugas` (
  `idpetugas` int(11) NOT NULL,
  `namapetugas` varchar(255) NOT NULL,
  `jeniskelamin` varchar(255) NOT NULL,
  `alamat` text NOT NULL,
  `nohp` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `distributor`
--
ALTER TABLE `distributor`
  ADD PRIMARY KEY (`iddistributor`);

--
-- Indeks untuk tabel `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`idobat`);

--
-- Indeks untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`nofaktur`);

--
-- Indeks untuk tabel `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`idpetugas`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `distributor`
--
ALTER TABLE `distributor`
  MODIFY `iddistributor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `member`
--
ALTER TABLE `member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `obat`
--
ALTER TABLE `obat`
  MODIFY `idobat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `nofaktur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=322;

--
-- AUTO_INCREMENT untuk tabel `petugas`
--
ALTER TABLE `petugas`
  MODIFY `idpetugas` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
