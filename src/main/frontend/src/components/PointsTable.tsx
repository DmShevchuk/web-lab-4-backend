import { PointAttempt } from "../utils/ApiClient"
import loaderImage from '../images/loader.svg';

interface PointsTableProps {
    points: PointAttempt[],
    showLoader: boolean,
    totalPointsCount: number,
    onlyOwned: boolean,
    setOnlyOwned(onlyOwned: boolean):void
};

export default function PointsTable({points, showLoader, totalPointsCount, onlyOwned, setOnlyOwned}: PointsTableProps) {
    return (
        <>
            <div className='flex flex-row flex-nowrap items-baseline justify-between'>
                <div className='mb-1'>
                    <h1 className='font-extrabold text-xl'>Выстрелы</h1>
                </div>
                <h1 className='text-xs'>На экране {points.length} из {totalPointsCount}</h1>
            </div>
            <div className='overflow-x-scroll rounded-xl'>
                <table className='w-full text-center text-xs md:text-base'>
                    <thead>
                        <tr className='bg-green-500 text-white'>
                            <th className='p-3'>id</th>
                            <th className='p-3'>X</th>
                            <th className='p-3'>Y</th>
                            <th className='p-3'>R</th>
                            <th className='p-3'>Время</th>
                            <th className='p-3'>Исполнение</th>
                            <th className='p-3'>Результат</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            !showLoader && points.map((v) => (
                                <tr className='odd:bg-green-100' key={v.id}>
                                    <td>{v.id}</td>
                                    <td>{v.x}</td>
                                    <td>{v.y}</td>
                                    <td>{v.r}</td>
                                    <td>{new Date(v.timePoint).toLocaleString()}</td>
                                    <td>{v.executionTime} ms</td>
                                    <td>{v.success ? <span className="text-green-500">HIT</span> : <span className="text-red-500">MISS</span>}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            {showLoader && <img src={loaderImage} className='fill-green-500 mx-auto mt-3 p-2 w-10 h-10 bg-violet-500 rounded-xl' alt='Loading'/>}
        </>
    )
}