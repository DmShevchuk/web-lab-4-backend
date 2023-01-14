import { ButtonHTMLAttributes, DetailedHTMLProps } from "react"

export function Button(props: DetailedHTMLProps<ButtonHTMLAttributes<HTMLButtonElement>, HTMLButtonElement>) {
    return (
        <button 
            className='transition w-full bg-green-500 text-white rounded-xl px-3 py-2 mb-3 hover:bg-green-600 disabled:bg-gray-400'
            {...props}
        >
            {props.children}
        </button>
    )
}